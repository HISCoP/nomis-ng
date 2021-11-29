import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

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
        .get(`${url}domains/${id}/ovcServices`)
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

export const createDomain = (formData, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}domains`, formData)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Domain saved successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong, please contact administration");
                }
            }

        );
};

export const updateDomain = (id, formData, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}domains/${id}`, formData)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Domain updated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong, please try again..");
                }
            }

        );
};

export const deleteDomain = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}domains/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Domain deleted successfully!");
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const createDomainService = (formData, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}ovc-services`, formData)
        .then(response => {
            console.log(response.status)

            if(response ){
                onSuccess();
                toast.success("Service created successfully!");
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong, please try again...");
                }
            }

        );
};

export const updateDomainService = (id, formData, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}ovc-services/${id}`, formData)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Domain Service updated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong, please try again...");
                }
            }

        );
};

export const deleteDomainService = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}ovc-services/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Domain Service deleted successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong, please try again...");
                }
            }

        );
};
