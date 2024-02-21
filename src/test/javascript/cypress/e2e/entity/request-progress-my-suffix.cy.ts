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

describe('RequestProgress e2e test', () => {
  const requestProgressPageUrl = '/request-progress-my-suffix';
  const requestProgressPageUrlPattern = new RegExp('/request-progress-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestProgressSample = {};

  let requestProgress;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-progresses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-progresses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-progresses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestProgress) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-progresses/${requestProgress.id}`,
      }).then(() => {
        requestProgress = undefined;
      });
    }
  });

  it('RequestProgresses menu should load RequestProgresses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-progress-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestProgress').should('exist');
    cy.url().should('match', requestProgressPageUrlPattern);
  });

  describe('RequestProgress page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestProgressPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestProgress page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-progress-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('RequestProgress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-progresses',
          body: requestProgressSample,
        }).then(({ body }) => {
          requestProgress = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-progresses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [requestProgress],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestProgressPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestProgress page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestProgress');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressPageUrlPattern);
      });

      it('edit button click should load edit RequestProgress page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestProgress');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressPageUrlPattern);
      });

      it('edit button click should load edit RequestProgress page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestProgress');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestProgress', () => {
        cy.intercept('GET', '/api/request-progresses/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('requestProgress').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressPageUrlPattern);

        requestProgress = undefined;
      });
    });
  });

  describe('new RequestProgress page', () => {
    beforeEach(() => {
      cy.visit(`${requestProgressPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestProgress');
    });

    it('should create an instance of RequestProgress', () => {
      cy.get(`[data-cy="date"]`).type('2024-01-25');
      cy.get(`[data-cy="date"]`).blur();
      cy.get(`[data-cy="date"]`).should('have.value', '2024-01-25');

      cy.get(`[data-cy="description"]`).type('yahoo');
      cy.get(`[data-cy="description"]`).should('have.value', 'yahoo');

      cy.get(`[data-cy="type"]`).select('REPORT_3');

      cy.get(`[data-cy="status"]`).select('SUCCEED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        requestProgress = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requestProgressPageUrlPattern);
    });
  });
});
