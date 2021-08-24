import React, {useEffect,useState} from 'react';
import MaterialTable from 'material-table';
import {
    Card,
    CardBody, 
} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { Link } from 'react-router-dom';
import Button from "@material-ui/core/Button";
import { FaPlus } from "react-icons/fa";
import "@reach/menu-button/styles.css";
import VisibilityIcon from '@material-ui/icons/Visibility';
import Tooltip from '@material-ui/core/Tooltip';
import IconButton from '@material-ui/core/IconButton';
import { connect } from "react-redux";
import { fetchAllDomainServices } from "../../../actions/domainsServices";
import NewServices from './NewServices';



const DomainServiceList = (props) => {
    const [loading, setLoading] = useState('')
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const domainDetails = props.location.state!==null ? props.location.state : null
    console.log(domainDetails)
    useEffect(() => {
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllDomainServices(domainDetails.id, onSuccess, onError);
    }, []); //componentDidMount

    const openNewDomainModal = (row) => {
        toggleModal();
    }


    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">Domain - {domainDetails.name} </Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}>Add New Service </span>
                    </Button>

                </div>
                <MaterialTable
                    title="Find By Service "
                    columns={[
                        { title: "Domain ID", field: "domainId" },
                        {title: "Domain Name", field: "name"},
                        {title: "Domain Status", field: "status"},
                        {title: "Action", field: "action", filtering: false,},
                    ]}
                    isLoading={loading}   
                    data={[props.serviceList].map((row) => ({
                        domainId: row.id,
                        name: row.name,
                    
                        status: row.archived===0 ? 'Active' : "Inactive",
                        action: <Link to ={{ 
                                        pathname: "/domain-service",  
                                        state: row
                                    }} 
                                        style={{ cursor: "pointer", color: "blue", fontStyle: "bold"}}
                                    >
                                        <Tooltip title="View Domain Services">
                                            <IconButton aria-label="View Domain Services" >
                                                <VisibilityIcon color="primary"/>
                                            </IconButton>
                                        </Tooltip>
                                    </Link>
                        
                        }))}  
                    
                        //overriding action menu with props.actions
                        components={props.actions}
                        options={{
                            headerStyle: {
                                backgroundColor: "#9F9FA5",
                                color: "#000",
                            },
                            searchFieldStyle: {
                                width : '200%',
                                margingLeft: '250px',
                            },
                            filtering: true,
                            exportButton: false,
                            searchFieldAlignment: 'left',
                            actionsColumnIndex: -1
                        }}
                />
            </CardBody>
            <NewServices toggleModal={toggleModal} showModal={showModal} />
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        serviceList: state.domainServices.services
    };
  };
  const mapActionToProps = {
    fetchAllDomainServices: fetchAllDomainServices
  };
  
  export default connect(mapStateToProps, mapActionToProps)(DomainServiceList);
