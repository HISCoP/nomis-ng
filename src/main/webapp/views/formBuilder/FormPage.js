import React from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";

import { Card, CardContent } from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../views/Title/CardTitle";
// import FormHomePage from "../formBuilder/FormRendererModal";
import FormHomePage from "../formBuilder/FormHomePage"


const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }
}));

const GeneralFormSearch = props => {
    const classes = useStyles();

    return (
        <div>
            <Card className={classes.cardBottom}>
                <CardContent>
                    <Title>

                            <Link color="inherit" to ={{pathname: "form-builder"}}>
                            <Button
                                variant="contained"
                                color="primary"
                                className=" float-right mr-1"
                                startIcon={<AddIcon />}>
                                <span style={{textTransform: 'capitalize'}}>New </span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'lowercase'}}>Form </span>
                            </Button>
                        </Link>
                        <br />
                    </Title>
                    <br />
                    {/*<FormHomePage/>*/}
                </CardContent>
            </Card>
        </div>
    );
};

export default GeneralFormSearch;
