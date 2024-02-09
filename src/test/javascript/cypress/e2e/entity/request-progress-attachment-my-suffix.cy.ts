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

describe('RequestProgressAttachment e2e test', () => {
  const requestProgressAttachmentPageUrl = '/request-progress-attachment-my-suffix';
  const requestProgressAttachmentPageUrlPattern = new RegExp('/request-progress-attachment-my-suffix(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestProgressAttachmentSample = {};

  let requestProgressAttachment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-progress-attachments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-progress-attachments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-progress-attachments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestProgressAttachment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-progress-attachments/${requestProgressAttachment.id}`,
      }).then(() => {
        requestProgressAttachment = undefined;
      });
    }
  });

  it('RequestProgressAttachments menu should load RequestProgressAttachments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-progress-attachment-my-suffix');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestProgressAttachment').should('exist');
    cy.url().should('match', requestProgressAttachmentPageUrlPattern);
  });

  describe('RequestProgressAttachment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestProgressAttachmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestProgressAttachment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-progress-attachment-my-suffix/new$'));
        cy.getEntityCreateUpdateHeading('RequestProgressAttachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressAttachmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-progress-attachments',
          body: requestProgressAttachmentSample,
        }).then(({ body }) => {
          requestProgressAttachment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-progress-attachments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [requestProgressAttachment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestProgressAttachmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestProgressAttachment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestProgressAttachment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressAttachmentPageUrlPattern);
      });

      it('edit button click should load edit RequestProgressAttachment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestProgressAttachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressAttachmentPageUrlPattern);
      });

      it('edit button click should load edit RequestProgressAttachment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestProgressAttachment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressAttachmentPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestProgressAttachment', () => {
        cy.intercept('GET', '/api/request-progress-attachments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('requestProgressAttachment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', requestProgressAttachmentPageUrlPattern);

        requestProgressAttachment = undefined;
      });
    });
  });

  describe('new RequestProgressAttachment page', () => {
    beforeEach(() => {
      cy.visit(`${requestProgressAttachmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestProgressAttachment');
    });

    it('should create an instance of RequestProgressAttachment', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        requestProgressAttachment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', requestProgressAttachmentPageUrlPattern);
    });
  });
});
