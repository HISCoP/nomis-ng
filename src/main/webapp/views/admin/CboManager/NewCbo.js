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
import { createCbosSetup, updateCbo  } from "./../../../actions/cbos";
import { Spinner } from 'reactstrap';
import { TextArea } from 'semantic-ui-react';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewCbo = (props) => {
    const [loading, setLoading] = useState(false)
    const [showNewCbo, setShowNewCbo] = useState(false)
    const defaultValues = { id:"",  name:"", description:"", code:""};
    const [formData, setFormData] = useState( defaultValues)
    const [errors, setErrors] = useState({});
    //const [errors, setErrors] = useState({});
    const classes = useStyles()
  
    useEffect(() => {
        //for application CBO  edit, load form data
        //props.loadCbo();
        setFormData(props.formData ? props.formData : defaultValues);
        setShowNewCbo(false);
    },  [props.formData,  props.showModal]);

    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }
     /*****  Validation */
     const validate = () => {
        let temp = { ...errors };
        temp.name = formData.name
            ? ""
            : "Name is required";
            temp.description = formData.description
            ? ""
            : "Description is required";
        setErrors({
            ...temp,
        });
        return Object.values(temp).every((x) => x == "");
    };

    const createCbo = e => {
        
        e.preventDefault()
        if (validate()) {
            setLoading(true);
            const onSuccess = () => {
                setLoading(false);
                props.loadCbo();
                props.toggleModal()
            }
            const onError = () => {
                setLoading(false);
                props.toggleModal()
            }
            if(formData.id){
                props.updateCbo(formData.id, formData, onSuccess, onError)
                return
            }
            props.createCbosSetup(formData, onSuccess,onError)
        }
    }


    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">

                <Form onSubmit={createCbo}>
                    <ModalHeader toggle={props.toggleModal}>New CBO Setup </ModalHeader>
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
                                                
                                            />
                                             {errors.name !=="" ? (
                                                <span className={classes.error}>{errors.name}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>CBO Code</Label>
                                            <Input
                                                type='text'
                                                name='code'
                                                id='code'
                                                placeholder=' '
                                                value={formData.code}
                                                onChange={handleInputChange}
                                                maxlength="3"
                                                style={{textTransform: "upperCase" }} 
                                            />
                                             {errors.code !=="" ? (
                                                <span className={classes.error}>{errors.code}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Description (Address/Phone Number/Email)</Label>
                                            <Input
                                                type='textarea'
                                                name='description'
                                                id='description'
                                                placeholder=' '
                                                value={formData.description}
                                                onChange={handleInputChange}
                                                
                                            />
                                             {errors.description !=="" ? (
                                                <span className={classes.error}>{errors.description}</span>
                                            ) : "" }
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


export default connect(null, {createCbosSetup, updateCbo})(NewCbo);

