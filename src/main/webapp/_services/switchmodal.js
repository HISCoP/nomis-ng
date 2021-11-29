import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { makeStyles } from '@material-ui/core/styles'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";




const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const SwitchProject = (props) => {

    const classes = useStyles()
    const [modal, setModal] = useState(false); 
 



    return (

        <div >
           
            <Modal isOpen={modal} backdrop={true}  zIndex={"9999"}>
            <ModalHeader toggle={() => setModal(!modal)}> Switch Facility </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    
                                    <Col md={12}>
                                       
                                    </Col>

                                    
                                </Row>

                              
                            </CardBody>
                        </Card>
                    </ModalBody>

            </Modal>
        </div>
    );
}


export default SwitchProject;

