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

describe('ArtworkComplain e2e test', () => {
  const artworkComplainPageUrl = '/artwork-complain-my-suffix';
  const artworkComplainPageUrlPattern = new RegExp('/artwork-complain-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkComplainSample = {};

  let artworkComplain;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-complains+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-complains').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-complains/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artworkComplain) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-complains/${artworkComplain.id}`,
      }).then(() => {
        artworkComplain = undefined;
      });
    }
  });

  it('ArtworkComplains menu should load ArtworkComplains page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-complain-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkComplain').should('exist');
    cy.url().should('match', artworkComplainPageUrlPattern);
  });

  describe('ArtworkComplain page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkComplainPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkComplain page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-complain-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkComplain');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkComplainPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-complains',
          body: artworkComplainSample,
        }).then(({ body }) => {
          artworkComplain = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-complains+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [artworkComplain],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkComplainPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtworkComplain page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkComplain');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkComplainPageUrlPattern);
      });

      it('edit button click should load edit ArtworkComplain page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkComplain');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkComplainPageUrlPattern);
      });

      it('edit button click should load edit ArtworkComplain page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkComplain');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkComplainPageUrlPattern);
      });

      it('last delete button click should delete instance of ArtworkComplain', () => {
        cy.intercept('GET', '/api/artwork-complains/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('artworkComplain').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkComplainPageUrlPattern);

        artworkComplain = undefined;
      });
    });
  });

  describe('new ArtworkComplain page', () => {
    beforeEach(() => {
      cy.visit(`${artworkComplainPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkComplain');
    });

    it('should create an instance of ArtworkComplain', () => {
      cy.get(`[data-cy="content"]`).type('drat truthful');
      cy.get(`[data-cy="content"]`).should('have.value', 'drat truthful');

      cy.get(`[data-cy="status"]`).select('POSTED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        artworkComplain = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkComplainPageUrlPattern);
    });
  });
});
