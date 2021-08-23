import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";


export const fetchAllHouseHoldMember = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}household-members`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD_MEMBER,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const fetchHouseHoldMemberById = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}household-members/${id}`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD_MEMBER_BY_ID,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};