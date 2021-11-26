import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";


export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}implementers`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_IP,
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

export const createIp = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}ips`, data)
        .then(response => {
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

export const updateIp = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}ips/${id}`, data)
        .then(response => {
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

export const deleteIp = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}ips/${id}`)
        .then(response => {
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

