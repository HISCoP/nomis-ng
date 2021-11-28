import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
//import Select from "react-select/creatable";
import { createIp, updateIp  } from "./../../../actions/ip";
import { Spinner } from 'reactstrap';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewDonor = (props) => {
    const [loading, setLoading] = useState(false)
    //const [showNewCbo, setShowNewCbo] = useState(false)
    const defaultValues = { id:"",  name:"", description:"", code:""};
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const classes = useStyles()
  
    useEffect(() => {
        //for application CBO  edit, load form data
        //props.loadCbo();
        setFormData(props.formData ? props.formData : defaultValues);
        //setShowNewCbo(false);
    },  [props.formData,  props.showModal]);

    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }
    

    const createIpSetup = e => {
        
        e.preventDefault()
            setLoading(true);
            const onSuccess = () => {
                setLoading(false);
                props.loadIps();
                props.toggleModal()
            }
            const onError = () => {
                setLoading(false);
                props.toggleModal()
            }
            if(formData.id){
                props.updateIp(formData.id, formData, onSuccess, onError)
                return
            }
            props.createIp(formData, onSuccess,onError)

    }


    return (

        <div >
           
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">

                <Form onSubmit={createIpSetup}>
                    <ModalHeader toggle={props.toggleModal}>New Implementing Partner Setup </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Name</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleInputChange}
                                                required
                                            />
                                        </FormGroup>
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Description</Label>
                                            <Input
                                                type='text'
                                                name='description'
                                                id='description'
                                                placeholder=' '
                                                value={formData.description}
                                                onChange={handleInputChange}
                                                required
                                            />
                                        </FormGroup>
                                    </Col>

                                   
                                    
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}>
                                    Save  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={props.toggleModal}
                                    startIcon={<CancelIcon />}>
                                    Cancel
                                </MatButton>
                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}


export default connect(null, {createIp, updateIp})(NewDonor);

