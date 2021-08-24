
import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";


export const fetchAllCodeset = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}application-codesets`)
        .then(response => {
            console.log(response.data)
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CODESET,
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
