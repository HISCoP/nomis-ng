
import React from 'react';
import {  Modal, ModalHeader, ModalBody } from 'reactstrap';
import FormRenderer from "../../formBuilder/FormRenderer";
import {toast, ToastContainer} from "react-toastify";
import * as CODES from "../../../api/codes";

const NewCarePlan = (props) => {
  const {
    className
  } = props;


  const onSuccess = () => {
    props.reloadSearch();
    toast.success("Form saved successfully!", { appearance: "success" });
    props.toggle();
  }


  return (
      <div>
        <ToastContainer />
        <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size={"xl"}>
          <ModalHeader toggle={props.toggle}>New Care Plan</ModalHeader>
          <ModalBody>
            <FormRenderer
                formCode={CODES.CARE_PLAN}
                householdId={props.householdId}
                onSuccess={onSuccess}
            />
          </ModalBody>

        </Modal>
      </div>
  );

}

export default NewCarePlan;