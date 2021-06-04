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
    to: '/dashboard',
    icon: 'cilList',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Visualization',
    to: '/dashboard',
    icon: 'cilGraph',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Admin',
    to: '/admin',
    icon: 'cilJustifyCenter',
   }, 


]

export default _nav
