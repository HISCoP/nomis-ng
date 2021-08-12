import { combineReducers } from 'redux'

import menuReducer from "./menu";
import formReducers from "./formReducers"
import programManagerReducer from "./formManagerReducer"
import houseHoldMemberReducer from "./houseHoldMember"

export default combineReducers({
 
  menu: menuReducer,
  formReducers: formReducers,
  programManager: programManagerReducer,
  houseHoldMember: houseHoldMemberReducer,
})

