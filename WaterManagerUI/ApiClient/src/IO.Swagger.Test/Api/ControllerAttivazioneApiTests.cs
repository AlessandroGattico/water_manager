/* 
 * 
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 */
using System;
using System.IO;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reflection;
using RestSharp;
using NUnit.Framework;

using IO.Swagger.Client;
using IO.Swagger.Api;

namespace IO.Swagger.Test
{
    /// <summary>
    ///  Class for testing ControllerAttivazioneApi
    /// </summary>
    /// <remarks>
    /// This file is automatically generated by Swagger Codegen.
    /// Please update the test case below to test the API endpoint.
    /// </remarks>
    [TestFixture]
    public class ControllerAttivazioneApiTests
    {
        private ControllerAttivazioneApi instance;

        /// <summary>
        /// Setup before each unit test
        /// </summary>
        [SetUp]
        public void Init()
        {
            instance = new ControllerAttivazioneApi();
        }

        /// <summary>
        /// Clean up after each unit test
        /// </summary>
        [TearDown]
        public void Cleanup()
        {

        }

        /// <summary>
        /// Test an instance of ControllerAttivazioneApi
        /// </summary>
        [Test]
        public void InstanceTest()
        {
            // TODO uncomment below to test 'IsInstanceOfType' ControllerAttivazioneApi
            //Assert.IsInstanceOfType(typeof(ControllerAttivazioneApi), instance, "instance is a ControllerAttivazioneApi");
        }

        /// <summary>
        /// Test AddAttivazione
        /// </summary>
        [Test]
        public void AddAttivazioneTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //string body = null;
            //var response = instance.AddAttivazione(body);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test DeleteAttivazione
        /// </summary>
        [Test]
        public void DeleteAttivazioneTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.DeleteAttivazione(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test GetAttivazioneId
        /// </summary>
        [Test]
        public void GetAttivazioneIdTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.GetAttivazioneId(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
        /// <summary>
        /// Test GetAttivazioniAttuatore
        /// </summary>
        [Test]
        public void GetAttivazioniAttuatoreTest()
        {
            // TODO uncomment below to test the method and replace null with proper value
            //int? id = null;
            //var response = instance.GetAttivazioniAttuatore(id);
            //Assert.IsInstanceOf<string> (response, "response is string");
        }
    }

}
