import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/HouseholdSearch'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));
const MemberSearch = React.lazy(() => import('./views/household/member/MemberSearch'));
const FromBuilder = React.lazy(() => import('./views/formBuilder/ProgramManagerSearch'));
const FormPage = React.lazy(() => import('./views/formBuilder/FormPage'));

/* Admin */
const AdminHomePage = React.lazy(() => import('./views/admin/HomePage'));
const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/households', name: 'Households', component: houseHoldAssessment },
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage },
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage },
  { path: '/admin', name: 'Admin Dashboard', component: AdminHomePage },
  { path: '/household-members', name: 'Household Members', component: MemberSearch },
  { path: '/form-builder', name: 'Form Builder', component: FromBuilder },
  { path: "/form-home", name: 'Form Builder', component: FormPage} ,
  
];

export default routes;
