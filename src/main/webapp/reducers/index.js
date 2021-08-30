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
import reportReducer from './reportReducer';
import cboReducer from './cboReducer';
import donorReducer from './donorReducer';

export default combineReducers({
 
  menu: menuReducer,
  formReducers: formReducers,
  programManager: programManagerReducer,
  houseHoldMember: houseHoldMemberReducer,
  houseHold: houseHoldReducer,
  organizationalUnitReducer : organizationalUnitReducer,
  users: userReducer,
  reportReducer: reportReducer,
  codesetsReducer: codesetsReducer,
  globalVariables: globalVariableReducer,
  domainServices: domainsServicesReducer,
  roles: rolesReducer,
  cboReducer: cboReducer,
  donorReducer: donorReducer,

})

