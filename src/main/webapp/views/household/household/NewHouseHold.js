
import React, { useState } from 'react';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {connect} from "react-redux";
import axios from "axios";
import {url} from "../../../api";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import {CModal, CModalBody, CModalHeader} from "@coreui/react";
import "./tab.css";


const NewHouseHold = (props) => {
  const {
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);

  const currentForm = {
    code: CODES.NEW_HOUSEHOLD,
    formName: "Add Household",
    options:{
        hideHeader: true
    },
  };
    const editAssessment = (body) => {
        const onSuccess = () => {
            toast.success('Household assessment saved!');
            props.reloadSearch();
            props.toggle();

        }

        const onError = () => {
            toast.error('Error: Household not saved!');
        }
        axios
            .put(`${url}households/${props.household.id}`, body)
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
        assessment: data.assessment,
        householdMigrationDTOS: [{state: data.state, lga:data.lga, country:data.country, street :data.street, ward:data.ward, community:data.community, latitude: data.latitude, longitude:data.longitude}],
        uniqueId: data.uniqueId };

    console.log(assessment);

      createAssessment(assessment);
    //  props.togglestatus();
};

    const updateAssessment = (e) => {
        const data = e.data;
        const assessment = {details: data,
            assessment: data.assessment,
            householdMemberDTO: { details: data.primaryCareGiver, householdMemberType: 1},
            householdAddressDTOS: [{state: data.state, lga:data.lga, country:data.country, street :data.street, ward:data.ward, community:data.community, latitude: data.latitude, longitude:data.longitude}],
            uniqueId: data.uniqueId };
        console.log(assessment);

        editAssessment(assessment);
        //  props.togglestatus();
    };


  return (
    <div>
        {props.household && props.household.id ?
            <CModal show={props.modal} onClose={props.toggle} className={className} backdrop={true} size='xl'>
                <CModalHeader closeButton>Edit HouseHold</CModalHeader>
                <CModalBody>
                    <ToastContainer/>
                    <FormRenderer
                        formCode={currentForm.code}
                        submission={props.household && props.household.details ? {data:props.household.details} : null}
                        onSubmit={updateAssessment}
                    />
                </CModalBody>
                {/* <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter> */}
            </CModal>
            :
            <CModal show={props.modal} onClose={props.toggle} className={className} backdrop={true} size='xl'>
                <CModalHeader closeButton>New HouseHold</CModalHeader>
                <CModalBody>
                    <ToastContainer/>
                    <FormRenderer
                        formCode={currentForm.code}
                        programCode=""
                        onSubmit={saveAssessment}
                    />
                </CModalBody>
                {/* <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter> */}
            </CModal>


        }
      
    </div>
  );
  
}

const mapStateToProps = (state = { form: {} }) => {
  return {

  };
};

const mapActionToProps = {
  //  fetchHouseHoldById: fetchHouseHoldById,
};

export default connect(mapStateToProps, mapActionToProps)(NewHouseHold);