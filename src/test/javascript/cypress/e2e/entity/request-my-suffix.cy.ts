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

describe('Request e2e test', () => {
  const requestPageUrl = '/request-my-suffix';
  const requestPageUrlPattern = new RegExp('/request-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestSample = {};

  let request;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/requests+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/requests').as('postEntityRequest');
    cy.intercept('DELETE', '/api/requests/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (request) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/requests/${request.id}`,
      }).then(() => {
        request = undefined;
      });
    }
  });

  it('Requests menu should load Requests page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Request').should('exist');
    cy.url().should('match', requestPageUrlPattern);
  });

  describe('Request page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Request page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('Request');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/requests',
          body: requestSample,
        }).then(({ body }) => {
          request = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/requests+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [request],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Request page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('request');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestPageUrlPattern);
      });

      it('edit button click should load edit Request page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Request');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestPageUrlPattern);
      });

      it('edit button click should load edit Request page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Request');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestPageUrlPattern);
      });

      it('last delete button click should delete instance of Request', () => {
        cy.intercept('GET', '/api/requests/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('request').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestPageUrlPattern);

        request = undefined;
      });
    });
  });

  describe('new Request page', () => {
    beforeEach(() => {
      cy.visit(`${requestPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Request');
    });

    it('should create an instance of Request', () => {
      cy.get(`[data-cy="description"]`).type('sweltering');
      cy.get(`[data-cy="description"]`).should('have.value', 'sweltering');

      cy.get(`[data-cy="status"]`).select('ENDED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        request = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requestPageUrlPattern);
    });
  });
});
