import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/HouseholdSearch'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));
const MemberSearch = React.lazy(() => import('./views/household/member/MemberSearch'));
const FormBuilder = React.lazy(() => import('./views/formBuilder/formBuilder'));
const ViewForm = React.lazy(() => import('./views/formBuilder/ViewForm'));
const FormPage = React.lazy(() => import('./views/formBuilder/FormPage'));
const DataVisualisation = React.lazy(() => import('./views/pages/unConstruction'));
const RetrospectivePage = React.lazy(() => import('./views/retrospective/HomePage'));

/* Admin */
const AdminHomePage = React.lazy(() => import('./views/admin/HomePage'));
const UserSetupHomePage = React.lazy(() => import('./views/admin/Users/UserPage'));
const ApplicationCodeSetupHomePage = React.lazy(() => import('./views/admin/ApplicationCodeset/ApplicationCodesetSearch'));
const OrganisationUnitHomepage = React.lazy(() => import('./views/admin/OrganizationUnit/Index'));
const ParentOrganizationUnit = React.lazy(() => import("./views/admin/OrganizationUnit/ParentOrganizationalUnit"));
const ParentOrganizationUnitLevel = React.lazy(() => import("./views/admin/OrganizationUnit/ParentOrganizationalUnitLevel"));
const ProgramSetupHomePage = React.lazy(() => import('./views/admin/DomainManager/DomainManager'));
const DomainServices = React.lazy(() => import('./views/admin/DomainManager/DomainServices'));
const UserRegistration = React.lazy(() => import('./views/admin/Users/UserRegistration'));
const Roles = React.lazy(() => import('./views/admin/Roles/RolesPage'));
//const roles = React.lazy(() => import("components/Roles/RolesPage"))
const addRole = React.lazy(() => import("./views/admin/Roles/AddRole"))
/*Reporting components*/
const ReportBuilderPage = React.lazy(() => import('./views/admin/Reports/ReportHome'));
const ReportTemplate = React.lazy(() => import("./views/admin/Reports/ReportTemplate"));
const ReportPage = React.lazy(() => import("./views/admin//Reports/ReportingPage"));
 const JasperTemplateUpdate = React.lazy(() => import("./views/admin/Reports/JasperTemplateUpdate"));
const ReportView = React.lazy(() => import("./views/admin/Reports/ReportView"));
const CboManager = React.lazy(() => import("./views/admin/CboManager/CboManager"));
const DonorManager = React.lazy(() => import("./views/admin/DonorManager/DonorManager"));
const IpManager = React.lazy(() => import("./views/admin/IpManager/IpManager"));
//const DonorIpManager = React.lazy(() => import("./views/admin/DonorIpManager/DonorIpManager"));
const CboDonorIpManager = React.lazy(() => import("./views/admin/CboProject/CboProject"));
const LoginPage = React.lazy(() => import("./views/pages/login/Login"));

const routes = [
  { path: '/', exact: true, name: '', component: Dashboard , isPrivate: true},
  { path: '/dashboard', name: 'Dashboard', component: Dashboard, isPrivate: true },
  { path: '/households', name: 'Households', component: houseHoldAssessment , isPrivate: true},
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage , isPrivate: true},
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage , isPrivate: true},
  { path: '/admin', name: 'Admin Dashboard', component: AdminHomePage , isPrivate: true},
  { path: '/household-members', name: 'Household Members', component: MemberSearch, isPrivate: true },
  { path: '/form-builder', name: 'Form Builder', component: FormBuilder, isPrivate: true },
  { path: '/edit-form', name: 'Edit Form', component: ViewForm, isPrivate: true },
  { path: '/retrospective', name: 'Retrospective', component: RetrospectivePage, isPrivate: true},
  /* Administrative Link */
  { path: "/form-home", name: 'Form Builder', component: FormPage},
  { path: "/users", name: 'User Management', component: UserSetupHomePage},
  { path: "/application-codeset-home", name: 'Application Codeset', component: ApplicationCodeSetupHomePage},
  { path: "/program-setup-home", name: 'Domain Setup', component: ProgramSetupHomePage},
  { path: "/organisation-unit-home", name: 'Organisation Unit', component: OrganisationUnitHomepage},
  { path: "/admin-parent-organization-unit", name: 'Organisation Unit', component: ParentOrganizationUnit},
  { path: "/admin-parent-organization-unit-level", name: 'Organisation Unit', component: ParentOrganizationUnitLevel},
  { path: "/domain-service", name: 'Domain Services', component: DomainServices},
  { path: "/user-registration", name: 'User Registration', component: UserRegistration},
  { path: "/roles", name: 'Role', component: Roles},
  { path: "/add-role", name: 'Role', component: addRole},
  { path: "/report-builder", name: 'Report ', component: ReportBuilderPage},
   { path: "/build-report", name: 'Build Report', component: ReportTemplate},
  { path: "/template-update", name: 'Build Report', component: JasperTemplateUpdate},

  { path: "/report", name: 'Report', component: ReportPage},
  { path: "/report-view", name: 'Report View', component: ReportView},
  { path: "/cbo", name: 'CBO ', component: CboManager} ,
  { path: "/donor", name: 'Donor ', component: DonorManager} ,
  { path: "/ip", name: 'Implementing Partner ', component: IpManager} ,
  //{ path: "/donor-ip", name: 'Donor-Implementing Partner ', component: DonorIpManager} ,
  { path: "/cbo-donor-ip", name: 'CBO Project ', component: CboDonorIpManager} ,
  { path: "/visualisation", name: 'Data Visualisation', component: DataVisualisation} ,

  
];

export default routes;
