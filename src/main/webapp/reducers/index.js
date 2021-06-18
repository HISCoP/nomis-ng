import { combineReducers } from 'redux'

import menuReducer from "./menu";
import formReducers from "./formReducers"
import programManagerReducer from "./formManagerReducer"

export default combineReducers({
 
  menu: menuReducer,
  formReducers: formReducers,
  programManager: programManagerReducer,
})

