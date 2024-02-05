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

describe('ArtworkLike e2e test', () => {
  const artworkLikePageUrl = '/artwork-like-my-suffix';
  const artworkLikePageUrlPattern = new RegExp('/artwork-like-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkLikeSample = {};

  let artworkLike;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-likes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-likes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-likes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkLike) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-likes/${artworkLike.id}`,
      }).then(() => {
        artworkLike = undefined;
      });
    }
  });

  it('ArtworkLikes menu should load ArtworkLikes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-like-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkLike').should('exist');
    cy.url().should('match', artworkLikePageUrlPattern);
  });

  describe('ArtworkLike page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkLikePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkLike page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-like-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkLike');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkLikePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-likes',
          body: artworkLikeSample,
        }).then(({ body }) => {
          artworkLike = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-likes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkLike],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkLikePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkLike page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkLike');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkLikePageUrlPattern);
      });

      it('edit button click should load edit ArtworkLike page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkLike');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkLikePageUrlPattern);
      });

      it('edit button click should load edit ArtworkLike page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkLike');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkLikePageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkLike', () => {
        cy.intercept('GET', '/api/artwork-likes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkLike').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkLikePageUrlPattern);

        artworkLike = undefined;
      });
    });
  });

  describe('new ArtworkLike page', () => {
    beforeEach(() => {
      cy.visit(`${artworkLikePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkLike');
    });

    it('should create an instance of ArtworkLike', () => {
      cy.get(`[data-cy="createAt"]`).type('2024-01-26');
      cy.get(`[data-cy="createAt"]`).blur();
      cy.get(`[data-cy="createAt"]`).should('have.value', '2024-01-26');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkLike = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkLikePageUrlPattern);
    });
  });
});
