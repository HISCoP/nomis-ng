import * as ACTION_TYPES from '../actions/types'

const initialState = {
  list: [],
  members: [],
  errorMessage: [],

}

const houseHoldMemberReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.HOUSE_HOLD_MEMBER_CREATE_SERVICE:
      return { ...state, list: action.payload }
    
      case ACTION_TYPES.ERROR_HOUSE_HOLD_MEMBER_CREATE_SERVICE:
      return { ...state, errorMessage: action.payload}

    case ACTION_TYPES.FETCH_HOUSE_HOLD_MEMBER:
      return { ...state, members: [...action.payload] }

    default:
      return state
  }
}

export default houseHoldMemberReducer


