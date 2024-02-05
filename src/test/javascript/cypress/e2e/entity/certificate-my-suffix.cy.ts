import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Certificate e2e test', () => {
  const certificatePageUrl = '/certificate-my-suffix';
  const certificatePageUrlPattern = new RegExp('/certificate-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const certificateSample = {};

  let certificate;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/certificates+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/certificates').as('postEntityRequest');
    cy.intercept('DELETE', '/api/certificates/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (certificate) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/certificates/${certificate.id}`,
      }).then(() => {
        certificate = undefined;
      });
    }
  });

  it('Certificates menu should load Certificates page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('certificate-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Certificate').should('exist');
    cy.url().should('match', certificatePageUrlPattern);
  });

  describe('Certificate page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(certificatePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Certificate page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/certificate-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('Certificate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', certificatePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/certificates',
          body: certificateSample,
        }).then(({ body }) => {
          certificate = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/certificates+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [certificate],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(certificatePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Certificate page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('certificate');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', certificatePageUrlPattern);
      });

      it('edit button click should load edit Certificate page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Certificate');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', certificatePageUrlPattern);
      });

      it('edit button click should load edit Certificate page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Certificate');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', certificatePageUrlPattern);
      });

      it('last delete button click should delete instance of Certificate', () => {
        cy.intercept('GET', '/api/certificates/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('certificate').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', certificatePageUrlPattern);

        certificate = undefined;
      });
    });
  });

  describe('new Certificate page', () => {
    beforeEach(() => {
      cy.visit(`${certificatePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Certificate');
    });

    it('should create an instance of Certificate', () => {
      cy.get(`[data-cy="description"]`).type('tweet');
      cy.get(`[data-cy="description"]`).should('have.value', 'tweet');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        certificate = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', certificatePageUrlPattern);
    });
  });
});
