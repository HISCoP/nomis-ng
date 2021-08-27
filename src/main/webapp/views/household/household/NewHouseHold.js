
import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody} from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {connect} from "react-redux";
import axios from "axios";
import {url} from "../../../api";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";


const NewHouseHold = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);

  const currentForm = {
    code: CODES.NEW_HOUSEHOLD,
    //programCode: CODES.GENERAL_SERVICE,
    formName: "Add Household",
    options:{
        hideHeader: true
    },
  };

  const createAssessment = (body) => {
    const onSuccess = () => {
      toast.success('Household assessment saved!');
      props.reloadSearch();
      props.toggle();

    }

    const onError = () => {
      toast.error('Error: Household not saved!');
    }
    axios
        .post(`${url}households`, body)
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

const saveAssessment = (e) => {
    const data = e.data;
    const assessment = {details: data,
        householdMemberDTO: { details: data.primaryCareGiver, householdMemberType: 1},
        householdAddressDTOS: [{state: data.state, lga:data.lga, country:data.country, street :data.street, ward:data.ward, community:data.community, latitude: data.latitude, longitude:data.longitude}],
        uniqueId: data.uniqueId };
  console.log(assessment);

      createAssessment(assessment);
    //  props.togglestatus();
};


  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='xl'>
        <ModalHeader toggle={props.toggle}>New HouseHold</ModalHeader>
        <ModalBody>
          <ToastContainer />
          <FormRenderer
          formCode={currentForm.code}
          programCode=""
          onSubmit={saveAssessment}
          />
        </ModalBody>
        {/* <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter> */}
      </Modal>
      
    </div>
  );
  
}

const mapStateToProps = (state = { form: {} }) => {
  return {

  };
};

const mapActionToProps = {
 // saveHouseHold: saveHouseHold
};

export default connect(mapStateToProps, mapActionToProps)(NewHouseHold);