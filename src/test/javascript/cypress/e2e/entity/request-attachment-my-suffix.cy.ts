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

describe('RequestAttachment e2e test', () => {
  const requestAttachmentPageUrl = '/request-attachment-my-suffix';
  const requestAttachmentPageUrlPattern = new RegExp('/request-attachment-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestAttachmentSample = {};

  let requestAttachment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-attachments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-attachments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-attachments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestAttachment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-attachments/${requestAttachment.id}`,
      }).then(() => {
        requestAttachment = undefined;
      });
    }
  });

  it('RequestAttachments menu should load RequestAttachments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-attachment-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestAttachment').should('exist');
    cy.url().should('match', requestAttachmentPageUrlPattern);
  });

  describe('RequestAttachment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestAttachmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestAttachment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-attachment-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('RequestAttachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestAttachmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-attachments',
          body: requestAttachmentSample,
        }).then(({ body }) => {
          requestAttachment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-attachments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [requestAttachment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestAttachmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestAttachment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestAttachment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestAttachmentPageUrlPattern);
      });

      it('edit button click should load edit RequestAttachment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestAttachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestAttachmentPageUrlPattern);
      });

      it('edit button click should load edit RequestAttachment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestAttachment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestAttachmentPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestAttachment', () => {
        cy.intercept('GET', '/api/request-attachments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('requestAttachment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestAttachmentPageUrlPattern);

        requestAttachment = undefined;
      });
    });
  });

  describe('new RequestAttachment page', () => {
    beforeEach(() => {
      cy.visit(`${requestAttachmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestAttachment');
    });

    it('should create an instance of RequestAttachment', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        requestAttachment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requestAttachmentPageUrlPattern);
    });
  });
});
