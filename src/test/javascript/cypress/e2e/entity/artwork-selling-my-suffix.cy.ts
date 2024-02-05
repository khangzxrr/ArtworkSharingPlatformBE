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

describe('ArtworkSelling e2e test', () => {
  const artworkSellingPageUrl = '/artwork-selling-my-suffix';
  const artworkSellingPageUrlPattern = new RegExp('/artwork-selling-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkSellingSample = {};

  let artworkSelling;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-sellings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-sellings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-sellings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkSelling) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-sellings/${artworkSelling.id}`,
      }).then(() => {
        artworkSelling = undefined;
      });
    }
  });

  it('ArtworkSellings menu should load ArtworkSellings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-selling-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkSelling').should('exist');
    cy.url().should('match', artworkSellingPageUrlPattern);
  });

  describe('ArtworkSelling page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkSellingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkSelling page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-selling-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkSelling');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkSellingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-sellings',
          body: artworkSellingSample,
        }).then(({ body }) => {
          artworkSelling = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-sellings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkSelling],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkSellingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkSelling page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkSelling');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkSellingPageUrlPattern);
      });

      it('edit button click should load edit ArtworkSelling page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkSelling');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkSellingPageUrlPattern);
      });

      it('edit button click should load edit ArtworkSelling page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkSelling');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkSellingPageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkSelling', () => {
        cy.intercept('GET', '/api/artwork-sellings/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkSelling').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkSellingPageUrlPattern);

        artworkSelling = undefined;
      });
    });
  });

  describe('new ArtworkSelling page', () => {
    beforeEach(() => {
      cy.visit(`${artworkSellingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkSelling');
    });

    it('should create an instance of ArtworkSelling', () => {
      cy.get(`[data-cy="createAt"]`).type('2024-01-25');
      cy.get(`[data-cy="createAt"]`).blur();
      cy.get(`[data-cy="createAt"]`).should('have.value', '2024-01-25');

      cy.get(`[data-cy="type"]`).select('AUCTION');

      cy.get(`[data-cy="status"]`).select('ON_GOING');

      cy.get(`[data-cy="expectedSellingPrice"]`).type('12587');
      cy.get(`[data-cy="expectedSellingPrice"]`).should('have.value', '12587');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkSelling = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkSellingPageUrlPattern);
    });
  });
});
