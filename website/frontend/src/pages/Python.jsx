import React from "react";
import { NavLink } from "react-router-dom";
import "../styles.css";
const Python = () => {
  return (
    <div className="flex flex-col min-h-screen">
      {" "}
      <div className="container mx-auto mb-8">
        {" "}
        <h2 className="text-3xl font-bold text-center my-8" id="python_title">
          {" "}
          Python Resource Library{" "}
        </h2>{" "}
        <p className="text-1xl text-center" id="python_description">
          {" "}
          This page will contain resources to help you get started with Python
          and DRF.{" "}
        </p>{" "}
      </div>{" "}
      <div
        className="container mx-auto mt-12 grid grid-cols-1 md:grid-cols-2 gap-8"
        id="resources"
      >
        {" "}
        <div className="flex flex-col" id="py_index">
          {" "}
          <h4
            className="custom-font-size font-bold text-left text-green-800 my-8"
            id="drf_resource_title"
          >
            {" "}
            🐍 Python Resources{" "}
          </h4>{" "}
          <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2">
            {" "}
            <li>
              ⚙️ Data Types{" "}
              <ul className="ml-9 list-disc">
                {" "}
                <li>
                  {" "}
                  <NavLink
                    to="/documentation/data_types_type_conversion.md"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    📊 Data Types and Type Conversions{" "}
                  </NavLink>{" "}
                </li>{" "}
              </ul>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/variables.md"
                className="hover:text-blue-800"
              >
                {" "}
                🔄 Variables{" "}
              </NavLink>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/diff-between-django_repr_and_str.md"
                className="hover:text-blue-800"
              >
                {" "}
                🔤 `__repr__` vs `__str__` Comparison{" "}
              </NavLink>{" "}
            </li>{" "}
          </ul>{" "}
        </div>{" "}
        {/* Django Resources Column */}{" "}
        <div className="flex flex-col" id="django_index">
          {" "}
          <h4
            className="custom-font-size font-bold text-left text-blue-600 my-8"
            id="drf_resource_title"
          >
            {" "}
            🌐 Django Rest Framework (DRF) Resources{" "}
          </h4>{" "}
          <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2 text-gray-700">
            {" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/django_orm_basics.md"
                className="hover:text-blue-800"
              >
                {" "}
                🧩 Django ORM Basics{" "}
              </NavLink>{" "}
            </li>{" "}
            <li>
              📝 Django Migrations{" "}
              <ul className="ml-9 list-disc">
                {" "}
                <li>
                  {" "}
                  <NavLink
                    to="/documentation/django-migrations-fundamentals.md"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    📘 Django Migrations Fundamentals{" "}
                  </NavLink>{" "}
                </li>{" "}
                <li>
                  {" "}
                  <NavLink
                    to="/documentation/django-migrations-examples-guide.md"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    🗄️ Django Migrations Examples{" "}
                  </NavLink>{" "}
                </li>{" "}
              </ul>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/django_logging_configuration.md"
                className="hover:text-blue-800"
              >
                {" "}
                📋 Django Logging Configuration{" "}
              </NavLink>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/django-models-and-app-config-guide.md"
                className="hover:text-blue-800"
              >
                {" "}
                🏗️ Django Models and App Configuration Guide{" "}
              </NavLink>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/django-simple-restful-api-guide.md"
                className="hover:text-blue-800"
              >
                {" "}
                ⚙️ Building a Simple RESTful API with DRF{" "}
              </NavLink>{" "}
            </li>{" "}
            <li>
              {" "}
              <NavLink
                to="/documentation/rendering_markdown_doc_as_html.md"
                className="hover:text-blue-800"
              >
                {" "}
                🌐 Rendering Markdown as HTML{" "}
              </NavLink>{" "}
            </li>{" "}
          </ul>{" "}
        </div>{" "}
      </div>{" "}
    </div>
  );
};
export default Python;