<div align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E" alt="JavaScript"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5"/>
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3"/>
</div>

# 🏥 Hospital Management System

## 📖 Project Overview
The **Hospital Management System** is a robust, full-stack enterprise web application designed to streamline healthcare facility operations. It provides a seamless experience for patients, doctors, and administrators by bridging the gap between appointment scheduling, medical records management, and billing. 

**Main Purpose and Features:**
- 👨‍⚕️ **Role-Based Dashboards**: Tailored portals for Patients, Doctors, and Administrators.
- 📅 **Smart Appointment Booking**: Real-time availability, date-aware scheduling, and automated time slot generation.
- 💳 **Integrated Billing & Payments**: Secure payment processing utilizing Stripe integration.
- 🔐 **Authentication & Security**: JWT-based secure login and session management via Spring Security.
- 📧 **Automated Notifications**: Email confirmations and password reset capabilities.
- 🩺 **Doctor & Patient Management**: Easy oversight of medical profiles, appointment histories, and statuses.

---

## 📂 Folder Structure Explanation

The project follows a clean, decoupled architecture separating the RESTful backend API from the vanilla web frontend.

```text
Hospital_Managment_System/
├── frontend/                 # Frontend application (Vanilla Web Stack)
│   ├── css/                  # Unified design system and stylesheets
│   ├── js/                   # Client-side logic, API integration, and validation
│   └── *.html                # UI views (e.g., dashboards, booking, payments)
├── src/                      # Backend application (Spring Boot)
│   ├── main/java/            # Core Java source code
│   │   └── com/project/      # Controllers, Services, Repos, Entities, DTOs, Security
│   ├── main/resources/       # Configuration files (application.properties)
│   └── test/                 # Unit and integration tests
├── pom.xml                   # Maven dependencies and build configuration
└── mvnw / mvnw.cmd           # Maven wrapper scripts for reliable builds
```

---

## 🚀 Setup Instructions

Follow these steps to get the project running on your local machine.

### Prerequisites
- **Java 21** or higher
- **Maven** (or use the included wrapper)
- **MySQL Server**
- **Node.js / Live Server** (Optional, for serving the frontend)

### Environment Setup (Backend)
1. **Clone the repository:**
   ```bash
   git clone https://github.com/[YOUR-USERNAME]/Hospital_Managment_System.git
   cd Hospital_Managment_System
   ```
2. **Configure the Database & Secrets:**
   Open `src/main/resources/application.properties` and update your MySQL credentials and Stripe API keys:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   
   stripe.api.key=sk_test_your_stripe_key_here
   jwt.secret=your_super_secure_jwt_secret_key_here
   ```

3. **Run the Spring Boot Application:**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
   *The API will be available at `http://localhost:8080`.*

### Environment Setup (Frontend)
1. Navigate to the `frontend/` directory.
2. Open `index.html` directly in your browser, or for the best experience, run a local web server (e.g., using VS Code's "Live Server" extension).
   ```bash
   # If using Python 3 to serve locally:
   cd frontend
   python -m http.server 5500
   ```
   *The frontend will be available at `http://localhost:5500`.*

---

## 🛠️ Technologies Used

### Backend
- **Framework:** Spring Boot 4.0.6, Spring Web MVC
- **Language:** Java 21
- **Security:** Spring Security, JWT (JSON Web Tokens)
- **Database ORM:** Spring Data JPA, Hibernate
- **Database:** MySQL
- **Integrations:** Stripe Java API (Payments), Spring Boot Mail (Emailing)
- **Utilities:** Lombok, MapStruct (DTO Mapping)

### Frontend
- **Core:** HTML5, CSS3, Vanilla JavaScript
- **Design:** Custom CSS Design System
- **Architecture:** Modular JS architecture with centralized state and API handlers

---

## 👥 Team Members

| Name | Role | GitHub Profile |
| :--- | :--- | :--- |
| **[Placeholder Name 1]** | Lead Backend Developer / Architect | [@github_handle_1](https://github.com/placeholder1) |
| **[Placeholder Name 2]** | Frontend Developer / UI-UX | [@github_handle_2](https://github.com/placeholder2) |
| **[Placeholder Name 3]** | Database Admin / QA | [@github_handle_3](https://github.com/placeholder3) |
| **[Placeholder Name 4]** | DevOps / Scrum Master | [@github_handle_4](https://github.com/placeholder4) |

---

## 🤝 Collaboration History & Workflow

This project was developed using Agile methodologies and strict version control practices:
- **Branching Strategy:** We followed a simplified **Git Flow**. `main` branch was strictly reserved for production-ready code. Features were developed on isolated `feature/feature-name` branches, while bug fixes utilized `bugfix/issue-name` branches.
- **Pull Requests (PRs) & Code Reviews:** All code was merged into `main` via Pull Requests. PRs required at least one peer review to ensure code quality, security standards, and architectural consistency before merging.
- **Commit Practices:** We utilized semantic commit messages (e.g., `feat: added Stripe payment gateway`, `fix: corrected UTC offset in appointment slots`) to maintain a readable and organized project history.

---

## 🌟 GitHub Best Practices Applied

- 📝 **Meaningful Commit Messages:** Consistent, descriptive commits that document the *why* and *what* of changes.
- 📁 **Organized Folder Structure:** Clear separation of concerns between frontend client and backend architecture (Controllers, Services, Repositories).
- ✨ **Clean Coding Standards:** Adherence to **SOLID** principles, utilization of software design patterns (Facade, Factory, Observer), and strict DTO usage to prevent data over-fetching and infinite recursion.

---

## 🔮 Future Improvements

- [ ] **Telemedicine Integration:** Implement WebRTC for live virtual video consultations.
- [ ] **Predictive Analytics:** Integrate a machine learning model to predict patient no-shows based on historical data.
- [ ] **Mobile Application:** Port the frontend logic to a React Native or Flutter app for native iOS/Android experiences.
- [ ] **Push Notifications:** Add WebSockets and Service Workers for real-time browser push notifications.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details. 
*Academic Honesty Note: This repository was created for educational purposes.*
