# Codefleet Repository

## Overview
**Codefleet** is a versatile monorepo designed for software developers, integrating Django-based REST APIs, a React frontend, and Selenium automation for web testing. It supports independent projects: **Python Django Projects:** [`bookslibrary`, `ecommerce`], **UI Automation:** `selenium`, and `website`. Each project is scalable, independently deployable, and managed via dedicated CI/CD workflows, catering to backend, frontend, and automation needs.

## Project Structure
The repository is organized as a monorepo with distinct directories for each project. For a detailed breakdown, see [project_structure.md](./docs/project_structure.md).

## Projects

### Python Django Projects
   ### Bookslibrary
   - **Purpose**: A Django-based REST API for managing a bookstore, supporting CRUD operations on books.
   - **Scalability**: Uses PostgreSQL, scalable via AWS RDS or Azure Database. Gunicorn handles request scaling.
   - **Independent Deployability**: Deployable via `docker-compose.yml` and `.github/workflows/bookslibrary_ci.yml` to AWS Elastic Beanstalk or Azure App Service.
   - **Tech Stack**: Django, Django REST Framework, PostgreSQL.
   - **Details**: [docs/bookslibrary.md](./docs/bookslibrary.md)

   ### Ecommerce
   - **Purpose**: A Django-based REST API with two services: `order_service` (orders management) and `user_service` (user management).
   - **Scalability**: PostgreSQL backend with Gunicorn for scaling, deployable to AWS ECS or Azure App Service.
   - **Independent Deployability**: Each service has its own Dockerfile and shares `.github/workflows/ecommerce_ci.yml` for isolated deployments.
   - **Tech Stack**: Django, Django REST Framework, PostgreSQL.
   - **Details**: [docs/djangocommerce.md](./docs/djangocommerce.md)

### UI Automation
   ### Selenium
   - **Purpose**: Java-based Selenium automation scripts for UI testing of the Herokuapp website (`PageInspector`) and Codefleet website (`CFInspector`).
   - **Scalability**: Supports parallel execution via Selenium Grid, deployable to AWS ECS or Azure VMs.
   - **Independent Deployability**: Uses `pom.xml`, `Dockerfile`, and `.github/workflows/selenium_ci.yml` (PageInspector) or `.github/workflows/website_and_cfinspector_ci.yml` (CFInspector) for isolated test runs.
   - **Tech Stack**: Java, Selenium WebDriver, TestNG, Maven.
   - **Details**: [docs/selenium.md](./docs/selenium.md)

### Website
- **Purpose**: A React-based frontend with a Django backend supporting authentication and Basic Auth testing for the Codefleet platform.
- **Scalability**: Static hosting via AWS S3 or Azure Static Web Apps, with horizontal scaling via CDN.
- **Independent Deployability**: Deployable via `Dockerfile` and `.github/workflows/website_and_cfinspector_ci.yml`.
- **Tech Stack**: React, Tailwind CSS, Node.js, Django, PostgreSQL.
- **Details**: [docs/website.md](./docs/website.md)

## Setup
1. **Clone the repository**:
   ```bash
   git clone https://github.com/vbgiyor/codefleet.git
   cd codefleet
   ```
2. **Set up environment**:
   - Configure environment variables in `.env` (e.g., `DATABASE_URL`, `SERVICE_TYPE`).
   - Install Docker and Docker Compose for containerized runs.
3. **Run locally**:
   - Follow project-specific instructions in respective Markdown files:
     - [Bookslibrary](./docs/bookslibrary.md)
     - [Ecommerce](./docs/djangocommerce.md)
     - [Selenium](./docs/selenium.md)
     - [Website](./docs/website.md)
4. **Run tests**:
   - Use GitHub Actions (`.github/workflows/`) or run locally (e.g., `python manage.py test`, `mvn test`, `npm test`).

## Deployment
Each project is independently deployable:
- **Bookslibrary**: AWS Elastic Beanstalk or Azure App Service.
- **Ecommerce**: AWS ECS or Azure App Service for each service.
- **Selenium**: AWS ECS or Azure VMs with Selenium Grid.
- **Website**: AWS S3/CloudFront or Azure Static Web Apps.
- CI/CD workflows (`deploy_aws.yml`, `deploy_azure.yml`) manage deployments.

## Documentation
- [Setup Instructions](./docs/setup.md)
- [Architecture Overview](./docs/architecture.md)
- [Project Structure](./docs/project_structure.md)

## Contributing
Contributions are welcome! Follow guidelines in [CONTRIBUTING.md](./CONTRIBUTING.md) (if available).

## License
Licensed under the MIT License. See [LICENSE](./LICENSE) for details.

*Last Updated: May 15, 2025*