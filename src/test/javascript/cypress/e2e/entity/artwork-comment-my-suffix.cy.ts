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

describe('ArtworkComment e2e test', () => {
  const artworkCommentPageUrl = '/artwork-comment-my-suffix';
  const artworkCommentPageUrlPattern = new RegExp('/artwork-comment-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkCommentSample = {};

  let artworkComment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-comments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-comments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-comments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkComment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-comments/${artworkComment.id}`,
      }).then(() => {
        artworkComment = undefined;
      });
    }
  });

  it('ArtworkComments menu should load ArtworkComments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-comment-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkComment').should('exist');
    cy.url().should('match', artworkCommentPageUrlPattern);
  });

  describe('ArtworkComment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkCommentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkComment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-comment-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkComment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCommentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-comments',
          body: artworkCommentSample,
        }).then(({ body }) => {
          artworkComment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-comments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkComment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkCommentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkComment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkComment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCommentPageUrlPattern);
      });

      it('edit button click should load edit ArtworkComment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkComment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCommentPageUrlPattern);
      });

      it('edit button click should load edit ArtworkComment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkComment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCommentPageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkComment', () => {
        cy.intercept('GET', '/api/artwork-comments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkComment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCommentPageUrlPattern);

        artworkComment = undefined;
      });
    });
  });

  describe('new ArtworkComment page', () => {
    beforeEach(() => {
      cy.visit(`${artworkCommentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkComment');
    });

    it('should create an instance of ArtworkComment', () => {
      cy.get(`[data-cy="content"]`).type('horror seldom');
      cy.get(`[data-cy="content"]`).should('have.value', 'horror seldom');

      cy.get(`[data-cy="createAt"]`).type('2024-01-26');
      cy.get(`[data-cy="createAt"]`).blur();
      cy.get(`[data-cy="createAt"]`).should('have.value', '2024-01-26');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkComment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkCommentPageUrlPattern);
    });
  });
});
