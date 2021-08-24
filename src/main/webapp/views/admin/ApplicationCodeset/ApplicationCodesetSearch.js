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
import { connect } from "react-redux";
import { fetchAllCodeset } from "../../../actions/codeSet";
import NewApplicationCodeset from "./NewApplicationCodeset";




const ApplicationCodesetList = (props) => {
    const [loading, setLoading] = useState('')
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    useEffect(() => {
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllCApplicationCodeset(onSuccess, onError);
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
                    <Typography color="textPrimary">Application Codeset </Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}>Add New Application Codeset </span>
                    </Button>

                </div>
                <MaterialTable
                    title="Find Application Codeset"
                    columns={[
                    {
                        title: "Codeset Group",
                        field: "codesetGroup",
                    },
                    { title: "Value", field: "display" },
                    { title: "Version", field: "version" },
                    { title: "Language", field: "language" },
                ]}
                isLoading={loading}
                data={props.applicationCodesetList.map((row) => ({
                    codesetGroup: row.codesetGroup,
                    id: row.id,
                    display: row.display,
                    language: row.language,
                    version: row.version
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
            <NewApplicationCodeset toggleModal={toggleModal} showModal={showModal} />
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        applicationCodesetList: state.codesetsReducer.codesetList
    };
  };
  const mapActionToProps = {
    fetchAllCApplicationCodeset: fetchAllCodeset
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ApplicationCodesetList);
