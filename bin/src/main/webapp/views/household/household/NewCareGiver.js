import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import {CARE_GIVER_ENROLMENT_FORM} from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast, ToastContainer} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const NewOvc = (props) => {
  const {
    className
  } = props;
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
<ToastContainer />
        <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='lg'>
          <ModalHeader toggle={props.toggle}>New Care Giver</ModalHeader>
          <ModalBody>
            <FormRenderer
                formCode={currentForm.code}
                onSubmit={save}/>
          </ModalBody>
        </Modal>
      </div>
  );

}

export default NewOvc;