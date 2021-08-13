import * as ACTION_TYPES from '../actions/types'

const initialState = {
  houseHoldList: [],
  holdHoldServiceHistory:[]

}

const houseHoldReducer = (state = initialState, action) => {
  switch (action.type) {
   
    case ACTION_TYPES.FETCH_HOUSE_HOLD:
      return { ...state, houseHoldList: [...action.payload] }

    case ACTION_TYPES.FETCH_HOUSEHOLD_SERVICE_HISTORY:
        return { ...state, holdHoldServiceHistory: [...action.payload] }

    default:
      return state
  }
}

export default houseHoldReducer


