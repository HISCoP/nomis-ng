import React from 'react'
import CIcon from '@coreui/icons-react'
import { Icon} from 'semantic-ui-react'

const _nav =  [
  {
    _tag: 'CSidebarNavItem',
    name: 'Dashboard',
    to: '/dashboard',
    icon: <CIcon name="cil-home" customClasses="c-sidebar-nav-icon"/>,
    
  },
  
  {
    _tag: 'CSidebarNavItem',
    icon:  <CIcon name="cil-home" customClasses="c-sidebar-nav-icon"/>,
    to: '/households',
    name: 'Household',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Household Members',
    to: '/household-members',
    icon: 'cilPeople',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Reports',
    to: '/report',
    icon: 'cilList',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Visualization',
    to: '#',
    icon: 'cilGraph',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Admin',
    to: '/admin',
    icon: 'cilJustifyCenter',
   }, 
   //{
  //   _tag: 'CSidebarNavItem',
  //   name: 'Users',
  //   to: '/household/household',
  //   icon: 'cilUser',
  // },
  // {
  //   _tag: 'CSidebarNavItem',
  //   name: 'Setting',
  //   to: '#',
  //   icon: 'cilApplicationsSettings',
  // },
 



  // {
  //   _tag: 'CSidebarNavItem',
  //   name: 'Disabled',
  //   icon: 'cil-ban',
  //   badge: {
  //     color: 'secondary',
  //     text: 'NEW',
  //   },
  //   addLinkClass: 'c-disabled',
  //   'disabled': true
  // },
  // {
  //   _tag: 'CSidebarNavDivider',
  //   className: 'm-2'
  // },
  // {
  //   _tag: 'CSidebarNavTitle',
  //   _children: ['Labels']
  // },
  // {
  //   _tag: 'CSidebarNavItem',
  //   name: 'Label danger',
  //   to: '',
  //   icon: {
  //     name: 'cil-star',
  //     className: 'text-danger'
  //   },
  //   label: true
  // },
  // {
  //   _tag: 'CSidebarNavItem',
  //   name: 'Label info',
  //   to: '',
  //   icon: {
  //     name: 'cil-star',
  //     className: 'text-info'
  //   },
  //   label: true
  // },
  // {
  //   _tag: 'CSidebarNavItem',
  //   name: 'Label warning',
  //   to: '',
  //   icon: {
  //     name: 'cil-star',
  //     className: 'text-warning'
  //   },
  //   label: true
  // },
  // {
  //   _tag: 'CSidebarNavDivider',
  //   className: 'm-2'
  // }
]

export default _nav
