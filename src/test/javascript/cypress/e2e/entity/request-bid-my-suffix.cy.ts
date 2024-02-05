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

describe('RequestBid e2e test', () => {
  const requestBidPageUrl = '/request-bid-my-suffix';
  const requestBidPageUrlPattern = new RegExp('/request-bid-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestBidSample = {};

  let requestBid;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-bids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-bids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-bids/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestBid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-bids/${requestBid.id}`,
      }).then(() => {
        requestBid = undefined;
      });
    }
  });

  it('RequestBids menu should load RequestBids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-bid-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestBid').should('exist');
    cy.url().should('match', requestBidPageUrlPattern);
  });

  describe('RequestBid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestBidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestBid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-bid-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('RequestBid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestBidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-bids',
          body: requestBidSample,
        }).then(({ body }) => {
          requestBid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-bids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [requestBid],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestBidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestBid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestBid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestBidPageUrlPattern);
      });

      it('edit button click should load edit RequestBid page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestBid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestBidPageUrlPattern);
      });

      it('edit button click should load edit RequestBid page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestBid');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestBidPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestBid', () => {
        cy.intercept('GET', '/api/request-bids/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('requestBid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestBidPageUrlPattern);

        requestBid = undefined;
      });
    });
  });

  describe('new RequestBid page', () => {
    beforeEach(() => {
      cy.visit(`${requestBidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestBid');
    });

    it('should create an instance of RequestBid', () => {
      cy.get(`[data-cy="description"]`).type('ick');
      cy.get(`[data-cy="description"]`).should('have.value', 'ick');

      cy.get(`[data-cy="price"]`).type('4275.1');
      cy.get(`[data-cy="price"]`).should('have.value', '4275.1');

      cy.get(`[data-cy="deadline"]`).type('9663');
      cy.get(`[data-cy="deadline"]`).should('have.value', '9663');

      cy.get(`[data-cy="status"]`).select('SELECTED_BID');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        requestBid = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requestBidPageUrlPattern);
    });
  });
});
