import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";


export const fetchAllHouseHold = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD,
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

export const fetchAllHouseHoldServiceHistory = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}encounters`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSEHOLD_SERVICE_HISTORY,
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

export const fetchHouseHoldById = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${id}`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD_BY_ID,
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