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

describe('SellingBid e2e test', () => {
  const sellingBidPageUrl = '/selling-bid-my-suffix';
  const sellingBidPageUrlPattern = new RegExp('/selling-bid-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sellingBidSample = {};

  let sellingBid;
  let walletTransaction;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/wallet-transactions',
      body: { amount: 1509.6, type: 'BUY', status: 'VERIFING', createAt: '2024-01-25' },
    }).then(({ body }) => {
      walletTransaction = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/selling-bids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/selling-bids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/selling-bids/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/wallet-transactions', {
      statusCode: 200,
      body: [walletTransaction],
    });

    cy.intercept('GET', '/api/artwork-sellings', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (sellingBid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/selling-bids/${sellingBid.id}`,
      }).then(() => {
        sellingBid = undefined;
      });
    }
  });

  afterEach(() => {
    if (walletTransaction) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/wallet-transactions/${walletTransaction.id}`,
      }).then(() => {
        walletTransaction = undefined;
      });
    }
  });

  it('SellingBids menu should load SellingBids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('selling-bid-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SellingBid').should('exist');
    cy.url().should('match', sellingBidPageUrlPattern);
  });

  describe('SellingBid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sellingBidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SellingBid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/selling-bid-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('SellingBid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellingBidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/selling-bids',
          body: {
            ...sellingBidSample,
            transaction: walletTransaction,
          },
        }).then(({ body }) => {
          sellingBid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/selling-bids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sellingBid],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(sellingBidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SellingBid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sellingBid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellingBidPageUrlPattern);
      });

      it('edit button click should load edit SellingBid page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SellingBid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellingBidPageUrlPattern);
      });

      it('edit button click should load edit SellingBid page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SellingBid');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellingBidPageUrlPattern);
      });

      it('last delete button click should delete instance of SellingBid', () => {
        cy.intercept('GET', '/api/selling-bids/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('sellingBid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sellingBidPageUrlPattern);

        sellingBid = undefined;
      });
    });
  });

  describe('new SellingBid page', () => {
    beforeEach(() => {
      cy.visit(`${sellingBidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SellingBid');
    });

    it('should create an instance of SellingBid', () => {
      cy.get(`[data-cy="bidPrice"]`).type('9881');
      cy.get(`[data-cy="bidPrice"]`).should('have.value', '9881');

      cy.get(`[data-cy="createAt"]`).type('2024-01-25');
      cy.get(`[data-cy="createAt"]`).blur();
      cy.get(`[data-cy="createAt"]`).should('have.value', '2024-01-25');

      cy.get(`[data-cy="status"]`).select('FAILED');

      cy.get(`[data-cy="transaction"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        sellingBid = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sellingBidPageUrlPattern);
    });
  });
});
