import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";


export const fetchAllCbos = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}cbos`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CBO,
                payload: response.data
            });
          
            onSuccess();
            
  
        })
        .catch(error => {
                if(onError){
                    onError();
                    
                }
            }

        );
};
export const createCbosSetup = (data, onSuccess , onError) => dispatch => {

    axios
        .post(`${url}cbos`, data)
        .then(response => {
            
            if(onSuccess){
                onSuccess();
                toast.success("CBO Setup saved successfully!")
                }
            
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const updateCbo = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}cbos/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("CBO Updated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something is wrong please try again...")
                }
            }

        );
};
export const deleteCbo = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}cbos/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("CBO Deleted successfully!")
            }
        })
        .catch(error => {
                console.log(error)
                if(onError){
                    onError();
                    toast.error("Something is wrong please try again...")
                }
            }

        );
};