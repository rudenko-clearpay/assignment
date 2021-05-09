import {Accordion, Card} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import {useSelector} from "react-redux";

const LoadMore = (props) => {
    const {onClick} = props;
    let [shouldShowLoadMoreBtn, setShowLoadMore] = useState();
    const page = useSelector(state => state.users.page || {totalPages: 0, number: 1});

    useEffect(() => {
        setShowLoadMore(page.number !== page.totalPages - 1);
    }, [page])

    return (<Card>
        <Accordion>
            {shouldShowLoadMoreBtn && <Button variant="outline-info" onClick={onClick}>Load More</Button>}
            {shouldShowLoadMoreBtn || <Button variant="outline-info" disabled>All users are loaded</Button>}
        </Accordion>
    </Card>)
}

export default LoadMore;