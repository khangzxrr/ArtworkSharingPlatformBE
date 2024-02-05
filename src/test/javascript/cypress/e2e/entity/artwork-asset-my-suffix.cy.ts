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

describe('ArtworkAsset e2e test', () => {
  const artworkAssetPageUrl = '/artwork-asset-my-suffix';
  const artworkAssetPageUrlPattern = new RegExp('/artwork-asset-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkAssetSample = {};

  let artworkAsset;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-assets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-assets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-assets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkAsset) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-assets/${artworkAsset.id}`,
      }).then(() => {
        artworkAsset = undefined;
      });
    }
  });

  it('ArtworkAssets menu should load ArtworkAssets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-asset-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkAsset').should('exist');
    cy.url().should('match', artworkAssetPageUrlPattern);
  });

  describe('ArtworkAsset page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkAssetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkAsset page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-asset-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkAsset');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkAssetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-assets',
          body: artworkAssetSample,
        }).then(({ body }) => {
          artworkAsset = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-assets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkAsset],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkAssetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkAsset page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkAsset');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkAssetPageUrlPattern);
      });

      it('edit button click should load edit ArtworkAsset page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkAsset');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkAssetPageUrlPattern);
      });

      it('edit button click should load edit ArtworkAsset page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkAsset');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkAssetPageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkAsset', () => {
        cy.intercept('GET', '/api/artwork-assets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkAsset').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkAssetPageUrlPattern);

        artworkAsset = undefined;
      });
    });
  });

  describe('new ArtworkAsset page', () => {
    beforeEach(() => {
      cy.visit(`${artworkAssetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkAsset');
    });

    it('should create an instance of ArtworkAsset', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkAsset = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkAssetPageUrlPattern);
    });
  });
});
