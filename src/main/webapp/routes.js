import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/HouseholdSearch'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));
const MemberSearch = React.lazy(() => import('./views/household/member/MemberSearch'));
const FormBuilder = React.lazy(() => import('./views/formBuilder/formBuilder'));
const ViewForm = React.lazy(() => import('./views/formBuilder/ViewForm'));
const FormPage = React.lazy(() => import('./views/formBuilder/FormPage'));
const DataVisualisation = React.lazy(() => import('./views/visualization/HomePage'));

/* Admin */
const AdminHomePage = React.lazy(() => import('./views/admin/HomePage'));
const UserSetupHomePage = React.lazy(() => import('./views/admin/Users/UserPage'));
const ApplicationCodeSetupHomePage = React.lazy(() => import('./views/admin/ApplicationCodeset/ApplicationCodesetSearch'));
const OrganisationUnitHomepage = React.lazy(() => import('./views/admin/OrganizationUnit/Index'));
const ProgramSetupHomePage = React.lazy(() => import('./views/admin/DomainManager/DomainManager'));
const DomainServices = React.lazy(() => import('./views/admin/DomainManager/DomainServices'));
const UserRegistration = React.lazy(() => import('./views/admin/Users/UserRegistration'));
const Roles = React.lazy(() => import('./views/admin/Roles/RolesPage'));
/*Reporting components*/
const ReportBuilderPage = React.lazy(() => import('./views/admin/Reports/ReportHome'));
const ReportTemplate = React.lazy(() => import("./views/admin/Reports/ReportTemplate"));
const ReportPage = React.lazy(() => import("./views/admin//Reports/ReportingPage"));
// const JasperTemplateUpdate = React.lazy(() => import("./views/admin/Reports/JasperTemplateUpdate"));
const ReportView = React.lazy(() => import("./views/admin/Reports/ReportView"));
const CboManager = React.lazy(() => import("./views/admin/CboManager/CboManager"));
const DonorManager = React.lazy(() => import("./views/admin/DonorManager/DonorManager"));

const routes = [
  { path: '/', exact: true, name: 'Home', component: Dashboard },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/households', name: 'Households', component: houseHoldAssessment },
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage },
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage },
  { path: '/admin', name: 'Admin Dashboard', component: AdminHomePage },
  { path: '/household-members', name: 'Household Members', component: MemberSearch },
  { path: '/form-builder', name: 'Form Builder', component: FormBuilder },
  { path: '/edit-form', name: 'Edit Form', component: ViewForm },
  /* Administrative Link */
  { path: "/form-home", name: 'Form Builder', component: FormPage},
  { path: "/user-setup-home", name: 'User Setup', component: UserSetupHomePage},
  { path: "/application-codeset-home", name: 'Application Codeset', component: ApplicationCodeSetupHomePage},
  { path: "/program-setup-home", name: 'Domain Setup', component: ProgramSetupHomePage},
  { path: "/organisation-unit-home", name: 'Organisation Unit', component: OrganisationUnitHomepage},
  { path: "/domain-service", name: 'Domain Services', component: DomainServices},
  { path: "/user-registration", name: 'User Registration', component: UserRegistration},
  { path: "/roles", name: 'Role', component: Roles},
  /* The rout to Report*/
  { path: "/report-builder", name: 'Report ', component: ReportBuilderPage},
   { path: "/build-report", name: 'Build Report', component: ReportTemplate},
  { path: "/report", name: 'Report', component: ReportPage},
  { path: "/report-view", name: 'Report View', component: ReportView},
  { path: "/cbo", name: 'CBO ', component: CboManager} ,
  { path: "/donor", name: 'Donor Manager', component: DonorManager} ,
  
];

export default routes;
