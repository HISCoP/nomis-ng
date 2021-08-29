import { combineReducers } from 'redux'

import menuReducer from "./menu";
import formReducers from "./formReducers"
import programManagerReducer from "./formManagerReducer"
import houseHoldMemberReducer from "./houseHoldMemberReducer"
import houseHoldReducer from "./houseHoldReducer"
import organizationalUnitReducer from './organizationalUnitReducer';
import userReducer from './userReducer'
import codesetsReducer from './codesetsReducer';
import globalVariableReducer from "./globalVariableReducer";
import domainsServicesReducer from './domainsServicesReducer';
import rolesReducer from './rolesReducer';


export default combineReducers({
 
  menu: menuReducer,
  formReducers: formReducers,
  programManager: programManagerReducer,
  houseHoldMember: houseHoldMemberReducer,
  houseHold: houseHoldReducer,
  organizationalUnitReducer : organizationalUnitReducer,
  users: userReducer,
  codesetsReducer: codesetsReducer,
  globalVariables: globalVariableReducer,
  domainServices: domainsServicesReducer,
  roles: rolesReducer,

})

