import axios from "axios";
import { url } from "api";
import * as ACTION_TYPES from "./types";


export const fetchAllCApplicationCodeset = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}application-codesets`)
        .then(response => {
            console.log(response.data)
            dispatch({
                type: ACTION_TYPES.APPLICATION_CODESET_LISTS,
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

export const createApplicationCodeset = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}application-codesets`, data)
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

export const updateApplicationCodeset = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}application-codesets/${id}`, data)
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

export const deleteApplicationCodeset = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}application-codesets/${id}`)
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

export const fetchAllWards = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}wards`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.WARD_LIST,
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

export const createWard = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}wards`, data)
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

export const updateWard = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}wards/${id}`, data)
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

export const deleteWard = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}wards/${id}`)
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