
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';

const NewCareGiver = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);




  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true}>
        <ModalHeader toggle={props.toggle}>New Care Giver</ModalHeader>
        <ModalBody>
          <p> Form will be here</p>
        </ModalBody>

      </Modal>
    </div>
  );
  
}

export default NewCareGiver;