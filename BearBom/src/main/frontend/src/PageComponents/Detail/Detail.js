import React, { useEffect, useRef } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import data from "../../ModuleComponents/data";
import "../../css/detail.css";
import { API_BASE_URL } from "../../app-config";
import axios from "axios";
import Thumb from "../Detail/Thumb";
import CourseNavbar from "./Navbar";
import Teacher from "./Teacher";
import CarouselFadeExample from "../Detail/Test.js";
import Cur from "./Detail-Cur";
import Time from "./Detail-Time";
import Location from "../Detail/Location";
import Notice from "./Detail-Notice";
import Apply from "../Detail/Apply";
import OpenModal from "./OpenModal";
import Review from "../Detail/Detail-Review";
import Test123 from "./Test123";

const Detail = ({ scrollTop }) => {
  // 아래에 왜 초기값을 객체 형태로 주지 않으면 오류가 나는지 모르겠음
  const [course, setCourse] = useState(
    data
    // {
    //   course_idx: "0",
    //   course_nm: "happy lecture",
    // },
  );
  const { id } = useParams(data);

  // a.id: data의 id속성
  // id: useParam으로 불러온 url의 숫자값
  let item = course.find((a) => (a.course_idx = id));
  console.log(item);

  const [reviewInfo, setReviewInfo] = useState({ courseIdx: 1 });

  const addReviewInfo = (e) => {
    const newReviewInfo = {
      ...reviewInfo,
      [e.target.name]: e.target.value,
    };

    setReviewInfo(newReviewInfo);
  };

  const onWriteReview = () => {
    console.log({
      ...reviewInfo,
    });

    axios({
      method: "post",
      url: API_BASE_URL + "/api/course/writeReview",
      // headers: {
      //   Authorization: "Bearer " + localStorage.getItem("ACCESS_TOKEN"),
      // },

      data: reviewInfo,
    }).then((response) => {
      console.log(response);
      // window.location.href = "/course/:id";
    });
  };
  const [reviewList, setReviewList] = useState([]);

  let listUrl = "http://localhost:8080/api/course/getReviewList";

  const list = () => {
    axios({
      url: listUrl,
      method: "post",
      data: { courseIdx: 1 },
    })
      .then((response) => {
        console.log(response.data.data);
        setReviewList(response.data.data);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  React.useEffect(() => {
    list();
  }, []);

  // const style = { onclick=
  //   overflow: user.active ? "none" : "hidden",
  // };
  const [woobin, setWoobin] = useState(false);

  return (
    <>
      <div className="main-container">
        <div className="info">
          <div className="main-info">
            <div className="main-img-box">
              {/* <Thumb></Thumb> */}
              <Test123></Test123>
            </div>
            <h4>Title</h4>
            <CourseNavbar />
            <hr />
            <section id="teacher" className="section-box">
              <h5>
                <b>강사소개</b>
              </h5>
              <Teacher />
            </section>
            <hr />
            <section id="class" className="section-box">
              <h5>
                <b>클래스소개</b>
              </h5>
              <div className="class-content">
                <CarouselFadeExample />
              </div>
            </section>
            <hr />
            <section id="cur" className="section-box">
              <Cur />
            </section>
            <hr />
            <section id="time" className="section-box">
              <Time />
            </section>
            <hr />
            <section id="loc" className="section-box">
              <Location />
            </section>
            <hr />
            <section id="notice" className="section-box">
              <Notice />
            </section>
            <hr />
            <section id="review" className="section-box">
              <div
                className="reviewList"
                style={{ height: woobin ? "auto" : "300px" }}
              >
                <OpenModal
                  addReviewInfo={addReviewInfo}
                  onWriteReview={onWriteReview}
                />

                {reviewList.map((review) => (
                  <Review review={review} />
                ))}
              </div>
              <button
                className="more-btn"
                onClick={() => {
                  console.log(woobin);
                  setWoobin(!woobin);
                }}
              >
                더보기
              </button>
            </section>
          </div>
          <div className="main-cal">
            <div>
              <Apply />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Detail;