import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";


export const fetchAllDomains = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}domains`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_DOMAINS,
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

export const fetchAllDomainServices = (id, onSuccess , onError) => dispatch => {
    
    axios
        .get(`${url}domains/${id}`)
        .then(response => {
            console.log(response.data)
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_DOMAIN_SERVICES,
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

