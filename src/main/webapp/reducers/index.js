import { combineReducers } from 'redux'

import menuReducer from "./menu";
import formReducers from "./formReducers"
import programManagerReducer from "./formManagerReducer"
import houseHoldMemberReducer from "./houseHoldMemberReducer"
import houseHoldReducer from "./houseHoldReducer"

export default combineReducers({
 
  menu: menuReducer,
  formReducers: formReducers,
  programManager: programManagerReducer,
  houseHoldMember: houseHoldMemberReducer,
  houseHold: houseHoldReducer,
})

