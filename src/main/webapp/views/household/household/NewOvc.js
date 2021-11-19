
import React, { useState } from 'react';
import { CModal, CModalBody, CModalHeader} from "@coreui/react";
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast, ToastContainer} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const NewOvc = (props) => {

    const currentForm = {
        code: CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM,
        formName: "Vulnerable Children Form",
        options:{
            hideHeader: true
        },
    };

    const createMember = (body) => {

        axios
            .post(`${url}household-members`, body)
            .then(response => {
                toast.success('VC saved!');
                props.reload();
                props.toggle();
            })
            .catch(error => {
                toast.error('Error: VC not saved!');
                }

            );
    }

    const save = (e) => {
        //alert('Save Successfully');

        const data = e.data;
        const member = {details: data, householdMemberType: 2, householdId: props.householdId};
        createMember(member)

    };


  return (
    <div>
        {props.modal &&
        <CModal show={props.modal} onClose={props.toggle} backdrop={true} size='xl'>
            <CModalHeader closeButton>New VC Enrolment</CModalHeader>
            <CModalBody>
                <FormRenderer
                    householdId={props.householdId}
                    formCode={currentForm.code}
                    programCode=""
                    submission={{}}
                    onSubmit={save}
                />
            </CModalBody>
        </CModal>
        }
    </div>
  );
  
}

export default NewOvc;