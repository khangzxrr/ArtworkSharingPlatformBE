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

describe('ArtworkCategory e2e test', () => {
  const artworkCategoryPageUrl = '/artwork-category-my-suffix';
  const artworkCategoryPageUrlPattern = new RegExp('/artwork-category-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkCategorySample = {};

  let artworkCategory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkCategory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-categories/${artworkCategory.id}`,
      }).then(() => {
        artworkCategory = undefined;
      });
    }
  });

  it('ArtworkCategories menu should load ArtworkCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-category-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkCategory').should('exist');
    cy.url().should('match', artworkCategoryPageUrlPattern);
  });

  describe('ArtworkCategory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkCategoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkCategory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-category-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCategoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-categories',
          body: artworkCategorySample,
        }).then(({ body }) => {
          artworkCategory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkCategory],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkCategoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkCategory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkCategory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCategoryPageUrlPattern);
      });

      it('edit button click should load edit ArtworkCategory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCategoryPageUrlPattern);
      });

      it('edit button click should load edit ArtworkCategory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkCategory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCategoryPageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkCategory', () => {
        cy.intercept('GET', '/api/artwork-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkCategory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCategoryPageUrlPattern);

        artworkCategory = undefined;
      });
    });
  });

  describe('new ArtworkCategory page', () => {
    beforeEach(() => {
      cy.visit(`${artworkCategoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkCategory');
    });

    it('should create an instance of ArtworkCategory', () => {
      cy.get(`[data-cy="name"]`).type('really incorporate');
      cy.get(`[data-cy="name"]`).should('have.value', 'really incorporate');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkCategory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkCategoryPageUrlPattern);
    });
  });
});
