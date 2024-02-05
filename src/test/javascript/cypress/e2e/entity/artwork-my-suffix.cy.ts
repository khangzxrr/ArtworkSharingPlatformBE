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

describe('Artwork e2e test', () => {
  const artworkPageUrl = '/artwork-my-suffix';
  const artworkPageUrlPattern = new RegExp('/artwork-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkSample = {};

  let artwork;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artworks+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artworks').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artworks/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artwork) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artworks/${artwork.id}`,
      }).then(() => {
        artwork = undefined;
      });
    }
  });

  it('Artworks menu should load Artworks page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Artwork').should('exist');
    cy.url().should('match', artworkPageUrlPattern);
  });

  describe('Artwork page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Artwork page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artworks',
          body: artworkSample,
        }).then(({ body }) => {
          artwork = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artworks+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artwork],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Artwork page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artwork');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('edit button click should load edit Artwork page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('edit button click should load edit Artwork page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('last delete button click should delete instance of Artwork', () => {
        cy.intercept('GET', '/api/artworks/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artwork').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);

        artwork = undefined;
      });
    });
  });

  describe('new Artwork page', () => {
    beforeEach(() => {
      cy.visit(`${artworkPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Artwork');
    });

    it('should create an instance of Artwork', () => {
      cy.get(`[data-cy="name"]`).type('aha');
      cy.get(`[data-cy="name"]`).should('have.value', 'aha');

      cy.get(`[data-cy="description"]`).type('double');
      cy.get(`[data-cy="description"]`).should('have.value', 'double');

      cy.get(`[data-cy="createAt"]`).type('self-assured');
      cy.get(`[data-cy="createAt"]`).should('have.value', 'self-assured');

      cy.get(`[data-cy="status"]`).select('DISABLE');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artwork = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkPageUrlPattern);
    });
  });
});
