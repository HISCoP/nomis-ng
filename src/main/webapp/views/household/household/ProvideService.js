
import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody, } from 'reactstrap';
import * as CODES from './../../../api/codes';
import FormRenderer from './../../formBuilder/FormRenderer';
import { connect } from "react-redux";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import {
  createProvideService,
} from "./../../../actions/householdCaregiverService";



const ProvideService = (props) => {
  const FormData = {};
  const {   
    className
  } = props;

  const currentForm = {
    code: CODES.Caregiver_Household_Service,
    //programCode: CODES.GENERAL_SERVICE,
    formName: "Hosehold Assesment",
    options:{
        hideHeader: true
    },
  };

  const saveAssessment = (e) => {
    console.log(props)
    const onSuccess = () => {
        props.toggle();
    };
    const onError = () => {
        props.toggle();
    };
    
    FormData['dateEncounter'] = e.data.dataGrid[0].date;
    FormData['formCode'] = CODES.Caregiver_Household_Service;
    FormData['data'] = e.data.dataGrid ;
    FormData['householdMemberId'] = props.memberId;
    
    console.log(FormData)
    props.createProvideService(FormData,onSuccess, onError);

};


  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='lg'>
        <ModalHeader toggle={props.toggle}>Provide Service</ModalHeader>
        <ModalBody>
          <FormRenderer
          formCode={currentForm.code}
          programCode=""
          onSubmit={saveAssessment}
          />
        </ModalBody>

      </Modal>
    </div>
  );
  
}


export default connect(null, { createProvideService })(
  ProvideService
);
