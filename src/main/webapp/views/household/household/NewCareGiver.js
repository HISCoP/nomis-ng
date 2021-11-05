import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import { CModal, CModalBody, CModalHeader} from "@coreui/react";
import {CARE_GIVER_ENROLMENT_FORM} from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const NewOvc = (props) => {

  const currentForm = {
    code: CARE_GIVER_ENROLMENT_FORM ,
    formName: "Care Giver Enrolment Form",
    options:{
      hideHeader: true
    },
  };

  const createMember = (body) => {
    const onSuccess = () => {
      toast.success('Household caregiver saved!');
      props.reload();
      props.toggle();

    }

    const onError = () => {
      toast.error('Error: Household caregiver not saved!');
    }
    axios
        .post(`${url}household-members`, body)
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
  }

  const save = (e) => {
    //alert('Save Successfully');

    const data = e.data;
    const member = {details: data, householdMemberType: 1, householdId: props.householdId};
   createMember(member)

  };


  return (
      <div>
          {props.modal &&
          <CModal show={props.modal} onClose={props.toggle} backdrop={true} size='xl'>
              <CModalHeader  closeButton >New Care Giver</CModalHeader>
              <CModalBody>
                  <FormRenderer
                      householdId={props.householdId}
                      formCode={currentForm.code}
                      submission={{}}
                      onSubmit={save} />
              </CModalBody>
          </CModal>

          }
       </div>
  );

}

export default NewOvc;