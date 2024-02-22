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

describe('Wallet e2e test', () => {
  const walletPageUrl = '/wallet-my-suffix';
  const walletPageUrlPattern = new RegExp('/wallet-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const walletSample = {};

  let wallet;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/wallets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/wallets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/wallets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (wallet) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/wallets/${wallet.id}`,
      }).then(() => {
        wallet = undefined;
      });
    }
  });

  it('Wallets menu should load Wallets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('wallet-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Wallet').should('exist');
    cy.url().should('match', walletPageUrlPattern);
  });

  describe('Wallet page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(walletPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Wallet page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/wallet-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('Wallet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', walletPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/wallets',
          body: walletSample,
        }).then(({ body }) => {
          wallet = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/wallets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [wallet],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(walletPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Wallet page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('wallet');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', walletPageUrlPattern);
      });

      it('edit button click should load edit Wallet page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Wallet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', walletPageUrlPattern);
      });

      it('edit button click should load edit Wallet page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Wallet');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', walletPageUrlPattern);
      });

      it('last delete button click should delete instance of Wallet', () => {
        cy.intercept('GET', '/api/wallets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('wallet').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', walletPageUrlPattern);

        wallet = undefined;
      });
    });
  });

  describe('new Wallet page', () => {
    beforeEach(() => {
      cy.visit(`${walletPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Wallet');
    });

    it('should create an instance of Wallet', () => {
      cy.get(`[data-cy="amount"]`).type('4984.92');
      cy.get(`[data-cy="amount"]`).should('have.value', '4984.92');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        wallet = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', walletPageUrlPattern);
    });
  });
});
