import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Input,Card,CardBody, Table} from 'reactstrap';
import { connect } from 'react-redux'
import {  CFormGroup } from '@coreui/react';
import MatButton from '@material-ui/core/Button'
import Button from "@material-ui/core/Button";
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import Select from "react-select";
import { createIp, updateIp  } from "../../../actions/ip";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";
import { CModal, CModalHeader, CModalBody,CForm} from '@coreui/react'
import { useSelector, useDispatch } from 'react-redux';
import { createCboProject } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))



const NewDonor = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const cboProjectList = useSelector(state => state.cboProjects.cboProjectList);
    const dispatch = useDispatch();
    const display = { stateId: "", lgaId: "", description: "", cboId:"", implementerId: "", donorId: "", organisationUnitIds:""}
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const [ipList, setipList] = useState([]);
    const [cboList, setcboList] = useState([]);
    const classes = useStyles()

    const [selectedOption, setSelectedOption] = useState(null);
    const [locationList, setLocationList] = useState({ stateName:"", lga:""})
    const [otherDetails, setOtherDetails] = useState(display);
    const [provinces, setProvinces] = useState([]);
    const [lgaDetail, setLgaDetail] = useState();
    const [stateDetail, setStateDetail] = useState();
    const [states, setStates] = useState([]);
   const [locationListArray2, setLocationListArray2] = useState([])

    useEffect(() => {
        //loadCboProjectList()
    }, []); //componentDidMount
  
    useEffect(() => {

        async function getCharacters() {
            axios
              .get(`${baseUrl}donors`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setdonorList(
                  Object.entries(response.data).map(([key, value]) => ({
                    label: value.name,
                    value: value.id,
                  }))
                );
              })
              .catch((error) => {
                
              });
          }
          getCharacters();
          setStateByCountryId();
    },  [  props.showModal]);
     /*  endpoint */

     useEffect(() => {
        async function getCharacters() {
            axios
              .get(`${baseUrl}implementers`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setipList(
                  Object.entries(response.data).map(([key, value]) => ({
                    label: value.name,
                    value: value.id,
                  }))
                );
              })
              .catch((error) => {
                
              });
          }
          getCharacters();
    },  []);
     /*  endpoint */


     useEffect(() => {
        async function getCharacters() {
            axios
              .get(`${baseUrl}cbos`)
              .then((response) => {
                //console.log(Object.entries(response.data));
                setcboList(
                  Object.entries(response.data).map(([key, value]) => ({
                    label: value.name,
                    value: value.id,
                  }))
                );
              })
              .catch((error) => {
                
              });
          }
          getCharacters();
    },  []);
     /*  endpoint */


//Get States from selected country
const  setStateByCountryId=() =>{
    async function getStateByCountryId() {
        const response = await axios.get(baseUrl + 'organisation-units/hierarchy/1/2')
        const stateList = response.data;
       // console.log(stateList)
        setStates(stateList);
    }
    getStateByCountryId();
}

//fetch province
const getProvinces = e => {
    setSelectedOption(null)
    const targetValue = e && e.target ? e.target : "" 
  setOtherDetails({ ...otherDetails, [targetValue.name]: targetValue.value });
        const stateId = targetValue.value ;
          if(stateId =="" || stateId==null){
            setLgaDetail("")
          }else{
            async function getCharacters() {
                const response = await axios.get(`${baseUrl}organisation-units/hierarchy/`+stateId+"/3");
                const newStates = states.filter(state => state.id == stateId)
                setStateDetail(newStates)
                setOtherDetails({...otherDetails, stateId:stateId})
                setProvinces(
                    Object.entries(response.data).map(([key, value]) => ({
                        label: value.name,
                        value: value.id,
                      }))
                   // response.data
                    
                    );
                    //const newStates = states.filter(state => state.id == otherDetails['stateId'])
                    const locationState = newStates[0].name
                    setLocationList({...locationList, stateName: locationState}) 
                    locationList['stateName']= locationState

            }
            getCharacters();
          }
};
const getlgaObj = e => {

    const newlga = provinces.filter(lga => lga.id == e.target.value)
    setLgaDetail(newlga)
    setOtherDetails({...otherDetails, lgaId:e.target.value})
}

    const handleInputChange = e => {
        setOtherDetails ({...otherDetails,  [e.target.name]: e.target.value});
    }
    
    var  locationListArray = []
    const addLocations = e => {
        console.log('test')
        //e.preventDefault()
        //Get the state selected
            
        setLocationList({...locationList,  lga:selectedOption })
        
      //locationListArray2.push(locationList)
        if(locationList['stateName'] !=='' && locationList['lga'] !==''){
        setLocationListArray2([...locationListArray2, ...[locationList]])
        setLocationList({stateName: "", lga:""})
        getProvinces()

        }
       
    }
    function LgaList (selectedOption){
        const  maxVal = []
        if (selectedOption != null && selectedOption.length > 0) {
          for(var i=0; i<selectedOption.length; i++){
             
                  if ( selectedOption[i].label!==null && selectedOption[i].label)
                        //console.log(selectedOption[i])
                            maxVal.push(selectedOption[i].label)
              
          }
        return maxVal.toString();
        }
    }

    const  RemoveItem = (e) => {
        const removeArr =locationListArray2.filter((element, index, array)  => index != e)
        setLocationListArray2(removeArr)
    }
    
    const organisationUnitIds = []

    const createCboAccountSetup = e => {
        e.preventDefault()
        
        const orgunitlga= locationListArray2.map(item => { 
            delete item['state'];
            
            item['lga'].map(itemLga => {
                console.log(itemLga['value'])
            organisationUnitIds.push(itemLga['value'])

            return itemLga;
            })
        })
        otherDetails['organisationUnitIds'] = organisationUnitIds
        delete otherDetails['lgaId'];
        delete otherDetails['stateId'];       
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
            history.push('/cbo-donor-ip')
            props.toggleModal() 
            props.loadIps() 
            setOtherDetails(display)         
        }
        const onError = () => {
            setLoading(false)  
            history.push('/cbo-donor-ip') 
            props.toggleModal() 
        }       
        dispatch(createCboProject(otherDetails,onSuccess, onError));
        
        return
 
    }
    


    return (

        <div >
            <ToastContainer />
            <CModal show={props.showModal} onClose={props.toggleModal} size="lg">

                <CForm >
                    <CModalHeader toggle={props.toggleModal}>NEW CBO PROJECT SETUP </CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Project Name *</Label>
                                            <Input
                                            type="text"
                                            name="description"
                                            id="description"
                                            value={otherDetails.description}
                                            onChange={handleInputChange}
                                            required
                                            />
                                           
                                        </FormGroup>
                                    </Col>
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Donors *</Label>
                                            <Input
                                            type="select"
                                            name="donorId"
                                            id="donorId"
                                            value={otherDetails.donorId}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {donorList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >Implementing Partners *</Label>
                                            <Input
                                            type="select"
                                            name="implementerId"
                                            id="implementerId"
                                            value={otherDetails.implementerId}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {ipList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >CBO *</Label>
                                            <Input
                                            type="select"
                                            name="cboId"
                                            id="cboId"
                                            value={otherDetails.cboId}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {cboList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <hr/>
                                        <h4>Location</h4>
                                    </Col>
                                    
                                    <Col md={5}>
                                        <FormGroup>
                                            <Label >State *</Label>
                                            <Input
                                                type="select"
                                                name="state"
                                                id="state"
                                                value={otherDetails.state}
                                                    onChange={getProvinces}
                                                >
                                                    <option >Please Select State</option>
                                                    {states.map((row) => (
                                                        <option key={row.id} value={row.id}>
                                                            {row.name}
                                                        </option>
                                                    ))}
                                                    
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={5}>
                                                <CFormGroup>
                                                    <Label >LGA</Label>
                                                    <Select
                                                        //defaultValue={selectedOption}
                                                        onChange={setSelectedOption}
                                                        //value={otherDetails.lga}
                                                        value={selectedOption}
                                                        options={provinces}
                                                        isMulti="true"
                                                        noOptionsMessage="true"
                                                    />
                                                   
                                                </CFormGroup>

                                            </Col>
                                    <Col md={2}>
                                    <Button variant="contained"
                                        color="primary"
                                        //startIcon={<FaPlus />} 
                                        style={{ marginTop:25}}
                                        size="small"
                                        onClick={addLocations}
                                        >
                                        <span style={{textTransform: 'capitalize'}}> Add</span>
                                    </Button>
                                    </Col>
                                    <Col md={12}>
                                    <div className={classes.demo}>
                                                      
                                                 
                                                            
                                                            {locationListArray2.length >0 
                                                            ?
                                                            <Table  striped responsive>
                                                                <thead >
                                                                    <tr>
                                                                        <th>State</th>
                                                                        <th>LGA</th>
                                                                        <th ></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                {locationListArray2.map((items, index) => (
                                                                    <tr>
                                                                        <th>{items.stateName}</th>
                                                                        <th>{LgaList(items.lga)}</th>
                                                                        
                                                                        <th >
                                                                            <IconButton aria-label="delete" size="small" color="error" onClick={() =>RemoveItem(index)}>
                                                                                <DeleteIcon fontSize="inherit" />
                                                                            </IconButton>
                                                                            
                                                                        </th>
                                                                    </tr>
                                                                    ) )}
                                                                </tbody>
                                                                </Table>
                                                            
                                                          
                                                          
                                                          : ""
                                                          }
                                                          
                                                     
                                                  </div>
                                    </Col>
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}
                                    onClick={createCboAccountSetup}
                                    
                                    >
                                    
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
                    </CModalBody>

                </CForm>
            </CModal>
        </div>
    );
}



export default connect(null, {createIp, updateIp})(NewDonor);

